public class RasterOperations {

    public Raster difference(Raster rasterA, Raster rasterB) {

        if (rasterA.getRows() != rasterB.getRows()
                || rasterA.getColumns() != rasterB.getColumns()) {

            throw new IllegalArgumentException(
                "Raster dimensions must match."
            );
        }

        int rows = rasterA.getRows();
        int columns = rasterA.getColumns();

        double[][] differenceData = new double[rows][columns];

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double valueA = rasterA.getValue(row, column);
                double valueB = rasterB.getValue(row, column);

                if (valueA == rasterA.getNoDataValue()
                        || valueB == rasterB.getNoDataValue()) {

                    differenceData[row][column] =
                        rasterA.getNoDataValue();

                } else {

                    differenceData[row][column] =
                        valueA - valueB;
                }
            }
        }

        return new Raster(
            differenceData,
            rasterA.getCellSize(),
            rasterA.getNoDataValue()
        );


        
    }




    public Raster normalize(Raster raster) {
 
    RasterAnalyzer analyzer = new RasterAnalyzer();

    double min = analyzer.minimum(raster);
    double max = analyzer.maximum(raster);

    int rows = raster.getRows();
    int columns = raster.getColumns();

    double[][] normalizedData = new double[rows][columns];

    if (max == min) {
        throw new IllegalArgumentException(
            "Cannot normalize a raster with identical minimum and maximum values."
        );
    }

    for (int row = 0; row < rows; row++) {

        for (int column = 0; column < columns; column++) {

            double value = raster.getValue(row, column);

            if (value == raster.getNoDataValue()) {

                normalizedData[row][column] =
                    raster.getNoDataValue();

            } else {

                normalizedData[row][column] =
                    (value - min) / (max - min);
            }
        }
    }

    return new Raster(
        normalizedData,
        raster.getCellSize(),
        raster.getNoDataValue()
    );
}

///////PHASE 3 — Complete the Raster Operations Engine//////
/// threshold()
/// 
/// 1 → value >= threshold
///0 → value < threshold
/*  
This is extremely useful later for:

vegetation masks
water masks
hazard masks
suitable/unsuitable areas
land-cover extraction
*/

public Raster threshold(Raster raster, double threshold) {

    int rows = raster.getRows();
    int columns = raster.getColumns();

    double[][] resultData = new double[rows][columns];

    for (int row = 0; row < rows; row++) {

        for (int column = 0; column < columns; column++) {

            double value = raster.getValue(row, column);

            if (value == raster.getNoDataValue()) {

                resultData[row][column] =
                    raster.getNoDataValue();

            } else if (value >= threshold) {

                resultData[row][column] = 1;

            } else {

                resultData[row][column] = 0;
            }
        }
    }

    return new Raster(
        resultData,
        raster.getCellSize(),
        raster.getNoDataValue()
    );
}


////reclassify()
/// 

public Raster reclassify(
        Raster raster,
        double[] breakpoints,
        int[] classValues) {

    if (classValues.length != breakpoints.length + 1) {

        throw new IllegalArgumentException(
            "Number of classes must be one greater than number of breakpoints."
        );
    }

    int rows = raster.getRows();
    int columns = raster.getColumns();

    double[][] resultData = new double[rows][columns];

    for (int row = 0; row < rows; row++) {

        for (int column = 0; column < columns; column++) {

            double value = raster.getValue(row, column);

            if (value == raster.getNoDataValue()) {

                resultData[row][column] =
                    raster.getNoDataValue();

                continue;
            }

            int classIndex = 0;

            while (classIndex < breakpoints.length
                    && value >= breakpoints[classIndex]) {

                classIndex++;
            }

            resultData[row][column] =
                classValues[classIndex];
        }
    }

    return new Raster(
        resultData,
        raster.getCellSize(),
        raster.getNoDataValue()
    );
}
}
