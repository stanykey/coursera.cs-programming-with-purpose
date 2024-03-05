import java.awt.Color;

public class KernelFilter {
    private static Picture applyFilter(Picture picture, double[][] kernel) {
        final int width = picture.width();
        final int height = picture.height();
        final int kernelSize = kernel.length;
        final int radius = kernelSize / 2;

        Picture filtered = new Picture(width, height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double sumR = 0;
                double sumG = 0;
                double sumB = 0;

                for (int i = 0; i < kernelSize; i++) {
                    for (int j = 0; j < kernelSize; j++) {
                        final int pixelX = (x + i - radius + width) % width;
                        final int pixelY = (y + j - radius + height) % height;

                        final Color color = picture.get(pixelX, pixelY);
                        final double weight = kernel[i][j];

                        // Accumulate weighted sum of RGB values
                        sumR += color.getRed() * weight;
                        sumG += color.getGreen() * weight;
                        sumB += color.getBlue() * weight;
                    }
                }

                // Normalize RGB values
                final int red = (int) Math.round(Math.min(Math.max(sumR, 0), 255));
                final int green = (int) Math.round(Math.min(Math.max(sumG, 0), 255));
                final int blue = (int) Math.round(Math.min(Math.max(sumB, 0), 255));

                // Set the color of the filtered pixel
                filtered.set(x, y, new Color(red, green, blue));
            }
        }

        return filtered;
    }

    // Returns a new picture that applies the identity filter to the given picture.
    public static Picture identity(Picture picture) {
        return new Picture(picture);
    }

    // Returns a new picture that applies a Gaussian blur filter to the given picture.
    public static Picture gaussian(Picture picture) {
        final double[][] kernel = {
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0},
            {2.0 / 16.0, 4.0 / 16.0, 2.0 / 16.0},
            {1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0},
        };
        return applyFilter(picture, kernel);
    }

    // Returns a new picture that applies a sharpen filter to the given picture.
    public static Picture sharpen(Picture picture) {
        final double[][] kernel = {
            { 0.0, -1.0,  0.0},
            {-1.0,  5.0, -1.0},
            { 0.0, -1.0,  0.0},
        };
        return applyFilter(picture, kernel);
    }

    // Returns a new picture that applies an Laplacian filter to the given picture.
    public static Picture laplacian(Picture picture) {
        final double[][] kernel = {
            {-1.0, -1.0, -1.0},
            {-1.0,  8.0, -1.0},
            {-1.0, -1.0, -1.0},
        };
        return applyFilter(picture, kernel);
    }

    // Returns a new picture that applies an emboss filter to the given picture.
    public static Picture emboss(Picture picture) {
        final double[][] kernel = {
            {-2.0, -1.0, 0.0},
            {-1.0,  1.0, 1.0},
            { 0.0,  1.0, 2.0},
        };
        return applyFilter(picture, kernel);
    }

    // Returns a new picture that applies a motion blur filter to the given picture.
    public static Picture motionBlur(Picture picture) {
        final double[][] kernel = {
            {1.0 / 9.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 1.0 / 9.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 1.0 / 9.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 1.0 / 9.0, 0.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 1.0 / 9.0, 0.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 1.0 / 9.0, 0.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 / 9.0, 0.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 / 9.0, 0.0},
            {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0 / 9.0},
        };
        return applyFilter(picture, kernel);
    }

    // Test client (ungraded).
    public static void main(String[] args) {
        final String filename = args[0];

        Picture picture = new Picture(filename);
        picture = identity(picture);
        picture = gaussian(picture);
        picture = sharpen(picture);
        picture = laplacian(picture);
        picture = emboss(picture);
        picture = motionBlur(picture);
        picture.show();
    }
}
