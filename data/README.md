# GeoRaster Explorer

A Java-based geospatial raster analysis application for environmental
analysis, raster processing, hazard modeling, and GeoTIFF generation.

## Overview

GeoRaster Explorer is a project-based geospatial analysis system developed
to explore how Java programming can be applied to Geographic Information
Systems (GIS), Remote Sensing, and environmental modeling.

The project implements a custom raster data structure and a collection of
raster analysis operations, environmental indices, change detection
methods, hazard classification techniques, and GeoTIFF input/output
capabilities.

A Khagrachhari, Bangladesh case study is used to demonstrate compound
hazard analysis using landslide and flood hazard surfaces.

## Objectives

- Build a reusable raster processing engine in Java
- Perform raster statistics and mathematical operations
- Implement environmental indices such as NDVI
- Perform raster normalization and change detection
- Read and write GeoTIFF datasets
- Combine multiple environmental hazard surfaces
- Produce GIS-compatible raster outputs
- Develop a foundation for future GeoAI and environmental applications

## Features

- Custom raster data structure
- Raster metadata management
- Raster statistics
- Minimum and maximum analysis
- Mean calculation
- Valid and NoData cell counting
- Raster normalization
- Raster difference
- Thresholding
- Raster reclassification
- NDVI calculation
- NDVI classification
- Environmental change detection
- GeoTIFF reading
- GeoTIFF export
- Multi-raster compatibility checking
- Compound hazard modeling
- Hazard classification
- QGIS-compatible output

## Technologies

- Java
- Maven
- GeoTools
- GIS
- Remote Sensing
- Raster Analysis
- GeoTIFF
- QGIS

## Project Architecture

The project follows a modular structure:

Raster
    ↓
RasterAnalyzer
    ↓
RasterOperations
    ↓
Environmental Analysis
    ↓
Hazard Modeling
    ↓
GeoTiffExporter
    ↓
GIS Visualization

## Raster Processing

The core `Raster` class represents raster datasets using a two-dimensional
array.

Each raster stores:

- Number of rows
- Number of columns
- Cell size
- NoData value
- Origin coordinates
- Coordinate reference system

This provides the foundation for subsequent raster analysis operations.

## Environmental Analysis

GeoRaster Explorer includes an NDVI analysis component.

NDVI is calculated using Near Infrared and Red reflectance values.

The project also includes NDVI classification and raster change detection
functionality.

## Raster Operations

The project currently supports:

- Difference
- Normalization
- Thresholding
- Reclassification

These operations form the computational foundation for more advanced
environmental models.

## Khagrachhari Case Study

The project includes a demonstration case study using landslide and flood
hazard rasters from Khagrachhari, Bangladesh.

The workflow is:

1. Load landslide raster
2. Load flood raster
3. Check raster compatibility
4. Normalize both hazard surfaces
5. Assign hazard weights
6. Calculate compound hazard index
7. Classify the index
8. Export the classified raster as GeoTIFF
9. Visualize the result in QGIS

## Compound Hazard Model

The current implementation uses an exploratory equal-weight weighted linear
combination.

The model combines normalized landslide and flood hazard surfaces using:

- Landslide weight = 0.5
- Flood weight = 0.5

The resulting raster represents an exploratory compound hazard index.

The current weights are demonstration values and are not presented as
scientifically validated hazard weights.

## Hazard Classification

The current demonstration classification uses four classes:

| Class | Category |
|------:|----------|
| 1 | Low |
| 2 | Moderate |
| 3 | High |
| 4 | Very High |

The classification thresholds are exploratory and have not been scientifically
validated for Khagrachhari.

## Results

The compound hazard model successfully produced a GIS-compatible GeoTIFF:

`output/Khagrachari_Compound_Hazard.tif`

The exported raster was subsequently loaded back into the application for
validation.

The validation confirmed that the exported GeoTIFF could be successfully
read and contained valid classified raster cells.

## QGIS Visualization

The generated GeoTIFF can be opened in QGIS for spatial visualization,
symbolization, and further GIS analysis.

A sample visualization is included in the `screenshots` directory.

## Validation

The exported compound hazard raster was independently loaded back into the
GeoRaster Explorer raster reader.

The validation process checked:

- Raster dimensions
- Cell size
- Geographic origin
- Coordinate reference system
- Hazard class counts
- NoData cells
- Total raster cells

## Limitations

The current version is a research and software-development prototype.

Important limitations include:

- Compound hazard weights are exploratory
- Classification thresholds are demonstration thresholds
- The current model uses equal weighting
- No expert-derived or statistically optimized weights are implemented
- Further NoData/NaN handling is required for greater robustness
- The current exporter assumes EPSG:4326 for the demonstration workflow
- The model does not yet incorporate a full multi-hazard statistical framework

## Future Development

Future versions may include:

- Configurable hazard weights
- Data-driven hazard weighting
- More environmental variables
- DEM-based terrain analysis
- Landslide susceptibility modeling
- Forest loss analysis
- Watershed analysis
- Spatial filtering
- Raster visualization
- Interactive Java GUI
- Machine learning integration
- Deep learning integration
- GeoAI capabilities
- WebGIS integration

## Project Status

Current status:

**Prototype / Research Portfolio Project**

The core raster engine, environmental analysis components, GeoTIFF
processing, compound hazard workflow, export functionality, and validation
workflow have been implemented.

## Author

Odri Chakma

Undergraduate Student  
Department of Geography and Environmental Studies  
University of Chittagong

## License

This project is intended for educational, research, and portfolio purposes.
