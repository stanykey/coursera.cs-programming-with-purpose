#include <array>
#include <cstdint>
#include <format>
#include <iostream>
#include <string_view>
#include <vector>


using Word = std::uint16_t;

enum class Opcode : std::uint8_t {
    HALT,
    ADD,
    SUBTRACT,
    BITWISE_AND,
    BITWISE_XOR,
    SHIFT_LEFT,
    SHIFT_RIGHT,
    LOAD_ADDR,
    LOAD,
    STORE,
    LOAD_INDIRECT,
    STORE_INDIRECT,
    BRANCH_ZERO,
    BRANCH_POSITIVE,
    JUMP_REGISTER,
    JUMP_AND_LINK
};


[[nodiscard]] constexpr bool is_address_operation(Opcode opcode) noexcept {
    switch (opcode) {
        case Opcode::LOAD_ADDR:
        case Opcode::LOAD:
        case Opcode::STORE:
        case Opcode::LOAD_INDIRECT:
        case Opcode::STORE_INDIRECT:
        case Opcode::BRANCH_ZERO:
        case Opcode::BRANCH_POSITIVE:
        case Opcode::JUMP_AND_LINK: {
            return true;
        }

        default: {
            return false;
        }
    }
}


struct Instruction {
    union Operands {
        struct {
            Word lhs;
            Word rhs;
        } registers;
        Word addr;
    };

    [[nodiscard]] static constexpr Operands extract_registers(Word data) noexcept {
        return {.registers = {static_cast<Word>((data >> 4) & 0xF), static_cast<Word>(data & 0xF)}};
    }

    [[nodiscard]] static constexpr Operands extract_address(Word data) noexcept {
        return {.addr = data};
    }

    [[nodiscard]] static constexpr Operands extract_operands(Opcode opcode, Word data) noexcept {
        return is_address_operation(opcode) ? extract_address(data) : extract_registers(data);
    }

    constexpr explicit Instruction(Word code) noexcept  // NOLINT: we need this implicit conversions
        : opcode(static_cast<Opcode>((code >> 12) & 0xF))
        , destination((code >> 8) & 0xF)
        , operands(extract_operands(opcode, code & 0xFF)) {
    }

    constexpr Instruction(Opcode opcode, Word destination, Word first, Word second) noexcept
        : opcode(opcode)
        , destination(destination)
        , operands({.registers = {.lhs = first, .rhs = second}}) {
    }

    constexpr Instruction(Opcode opcode, Word destination, Word addr) noexcept
        : opcode(opcode)
        , destination(destination)
        , operands({.addr = addr}) {
    }

    constexpr Instruction(Opcode opcode) noexcept
        : opcode(opcode)
        , destination(0x0)
        , operands({.addr = 0x00}) {
    }

    [[nodiscard]] constexpr bool is_address_based() const noexcept {
        return is_address_operation(opcode);
    }

    [[nodiscard]] constexpr bool is_halt() const noexcept {
        return opcode == Opcode::HALT;
    }

    Opcode   opcode;
    Word     destination;
    Operands operands;
};


class Toy {
public:
    static constexpr Word        PROGRAM_START_ADDRESS = 0x10;
    static constexpr std::size_t REGISTERS_COUNT       = 16;
    static constexpr std::size_t MEMORY_SIZE           = 256;

public:
    [[nodiscard]] constexpr Word register_value(std::size_t index) const noexcept {
        return registers_[index];
    }

    void dump(std::ostream& out) const {
        dump_registers(out);
        out << '\n';
        dump_memory(out);
    }

    constexpr void execute(const std::vector<Word>& program) noexcept {
        reset();
        load_program(program);

        Word program_counter = PROGRAM_START_ADDRESS;
        while (program_counter != 0xFF) {
            const Instruction instruction{memory_[program_counter]};
            if (instruction.is_halt()) {
                return;
            }
            program_counter = execute(program_counter, instruction);
        }
    }

    constexpr void execute(const std::vector<Instruction>& program) noexcept {
        reset();

        Word       program_counter = PROGRAM_START_ADDRESS;
        const auto index           = [&program_counter]() { return program_counter - PROGRAM_START_ADDRESS; };
        while (!program[index()].is_halt()) {
            program_counter = execute(program_counter, program[index()]);
        }
    }

    constexpr void reset() {
        registers_.fill(Word{});
        memory_.fill(Word{});
    }

private:
    constexpr void load_program(const std::vector<Word>& program) noexcept {
        const Word program_size = program.size();
        for (Word i = 0, addr = PROGRAM_START_ADDRESS; (i != program_size) && (addr != 0xFF); i++, addr++) {
            memory_[addr] = program[i];
        }
    }

    constexpr Word execute(Word program_counter, const Instruction& instruction) noexcept {
        program_counter++;

        const auto  dest     = instruction.destination;
        const auto& operands = instruction.operands;
        switch (instruction.opcode) {
            case Opcode::LOAD_ADDR: {
                registers_[dest] = operands.addr;
                break;
            }
            case Opcode::LOAD: {
                registers_[dest] = memory_[operands.addr];
                break;
            }
            case Opcode::STORE: {
                memory_[operands.addr] = registers_[dest];
                break;
            }
            case Opcode::LOAD_INDIRECT: {
                memory_[operands.addr] = memory_[registers_[dest]];
                break;
            }
            case Opcode::STORE_INDIRECT: {
                memory_[registers_[operands.addr]] = registers_[dest];
                break;
            }
            case Opcode::BRANCH_ZERO: {
                if (registers_[dest] == 0) {
                    program_counter = operands.addr;
                }
                break;
            }
            case Opcode::BRANCH_POSITIVE: {
                if (registers_[dest] > 0) {
                    program_counter = operands.addr;
                }
                break;
            }
            case Opcode::JUMP_AND_LINK: {
                registers_[dest] = program_counter + 1;
                program_counter  = operands.addr;
                break;
            }

            case Opcode::ADD: {
                registers_[dest] = registers_[operands.registers.lhs] + registers_[operands.registers.rhs];
                break;
            }
            case Opcode::SUBTRACT: {
                registers_[dest] = registers_[operands.registers.lhs] - registers_[operands.registers.rhs];
                break;
            }
            case Opcode::BITWISE_AND: {
                registers_[dest] = registers_[operands.registers.lhs] & registers_[operands.registers.rhs];
                break;
            }
            case Opcode::BITWISE_XOR: {
                registers_[dest] = registers_[operands.registers.lhs] ^ registers_[operands.registers.rhs];
                break;
            }
            case Opcode::SHIFT_LEFT: {
                registers_[dest] = registers_[operands.registers.lhs] << registers_[operands.registers.rhs];
                break;
            }
            case Opcode::SHIFT_RIGHT: {
                // need to cast the register value to the signed version to get the arithmetic shift,
                // and C++ does logical shift for unsigned values and arithmetic to signed
                const auto lhs   = static_cast<std::int16_t>(registers_[operands.registers.lhs]);
                registers_[dest] = lhs >> registers_[operands.registers.rhs];
                break;
            }

            case Opcode::JUMP_REGISTER: {
                program_counter = registers_[dest];
                break;
            }

            case Opcode::HALT: {
                // do nothing
                break;
            }
        }
        return program_counter;
    }

    void dump_registers(std::ostream& out) const {
        out << "Registers:\n";
        for (auto i = 0u; const auto item : registers_) {
            out << std::format("  [{:X}] = {:04X}\n", i++, item);
        }
    }

    void dump_memory(std::ostream& out) const {
        out << "Memory:\n";
        for (auto i = 0u; i != memory_.size(); i++) {
            if (i > 0 && (i % 16) == 0) {
                out << '\n';  // Start a new line for each group of 16 values
            }
            out << std::format("{:04X} ", memory_[i]);
        }
    }

private:
    std::array<Word, REGISTERS_COUNT> registers_{0};
    std::array<Word, MEMORY_SIZE>     memory_{0};
};


constexpr Toy execute_program(const std::vector<Word>& program) noexcept {
    Toy toy;
    toy.execute(program);
    return toy;
}

constexpr Toy execute_instructions(const std::vector<Instruction>& instructions) noexcept {
    Toy toy;
    toy.execute(instructions);
    return toy;
}


int main() {
    constexpr Instruction load_adder(0x7C0A);
    static_assert(load_adder.opcode == Opcode::LOAD_ADDR);
    static_assert(load_adder.destination == 0xC);
    static_assert(load_adder.is_address_based());
    static_assert(load_adder.operands.addr == 0x0A);

    constexpr Instruction add(0x1222);
    static_assert(add.opcode == Opcode::ADD);
    static_assert(add.destination == 0x2);
    static_assert(!add.is_address_based());
    static_assert(add.operands.registers.lhs == 0x02);
    static_assert(add.operands.registers.rhs == 0x02);

    constexpr Instruction subtract(0x2CC1);
    static_assert(subtract.opcode == Opcode::SUBTRACT);
    static_assert(subtract.destination == 0xC);
    static_assert(!subtract.is_address_based());
    static_assert(subtract.operands.registers.lhs == 0x0C);
    static_assert(subtract.operands.registers.rhs == 0x01);

    constexpr Instruction branch_positive(0xDC13);
    static_assert(branch_positive.opcode == Opcode::BRANCH_POSITIVE);
    static_assert(branch_positive.destination == 0xC);
    static_assert(branch_positive.is_address_based());
    static_assert(branch_positive.operands.addr == 0x13);

    constexpr Instruction halt(0x0000);
    static_assert(halt.opcode == Opcode::HALT);
    static_assert(halt.destination == 0x0);
    static_assert(halt.is_halt());

    static_assert(execute_program({0x7B01, 0x2A0B, 0x0000}).register_value(0xA) == 0xFFFF);
    static_assert(execute_program({0x7A01, 0x2A0A, 0x0000}).register_value(0xA) == 0xFFFF);
    static_assert(execute_program({0x7B01, 0x2B0A, 0x0000}).register_value(0xB) == 0);
    static_assert(execute_program({0x7AFF, 0x7B08, 0x5AAB, 0x6AAB, 0x0000}).register_value(0xA) == 0xFFFF);
    static_assert(execute_program({0x7AFF, 0x0000}).register_value(0xA) == 0xFF);

    // execute the next program:
    // int count = 10
    // int step = 1
    // int result = 1
    // while counter > 0
    //     result = result + result
    //     count = count - step
    //
    constexpr auto toy1 = execute_instructions({
        {Opcode::LOAD_ADDR, 0xC, 0x0A},
        {Opcode::LOAD_ADDR, 0x1, 0x01},
        {Opcode::LOAD_ADDR, 0x2, 0x01},
        {Opcode::ADD, 0x2, 0x2, 0x2},
        {Opcode::SUBTRACT, 0xC, 0xC, 0x1},
        {Opcode::BRANCH_POSITIVE, 0xC, 0x13},
        {Opcode::HALT},
    });
    static_assert(toy1.register_value(0x2) == 1024);
    // toy1.dump(std::cout);

    constexpr auto toy2 = execute_program({0x7C0A, 0x7101, 0x7201, 0x1222, 0x2CC1, 0xDC13, 0x0000});
    static_assert(toy2.register_value(0x2) == 1024);
    // toy2.dump(std::cout);

    constexpr auto toy3 = execute_program({0x8113, 0x2111, 0x9113, 0x8113, 0x0000});
    static_assert(toy3.register_value(0x1) == 0x0);
    // toy3.dump(std::cout);

    return toy3.register_value(0x1);
}
