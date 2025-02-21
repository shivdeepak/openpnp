package org.openpnp.vision.pipeline.stages;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openpnp.model.Length;
import org.openpnp.vision.pipeline.CvPipeline;
import org.openpnp.vision.pipeline.CvStage;
import org.openpnp.vision.pipeline.Property;
import org.openpnp.vision.pipeline.Stage;
import org.simpleframework.xml.Attribute;

@Stage(category="Image Processing", description="Performs gaussian blurring on the working image.")
public class BlurGaussian extends CvStage {
    @Attribute
    @Property(description="Width and height of the blurring kernel. Should be an odd number greater than or equal to 3")
    private int kernelSize = 3;

    @Attribute(required = false)
    @Property(description = "Name of the property through which OpenPnP controls this stage. Use \"BlurGaussian\" for standard control.")
    private String propertyName = "BlurGaussian";

    public int getKernelSize() {
        return kernelSize;
    }

    public void setKernelSize(int kernelSize) {
        this.kernelSize = 2 * (kernelSize / 2) + 1;
        this.kernelSize = this.kernelSize < 3 ? 3 : this.kernelSize;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public Result process(CvPipeline pipeline) throws Exception {
        int kernelSize = getPossiblePipelinePropertyOverride(this.kernelSize, pipeline, propertyName+".kernelSize",
                Double.class, Length.class)|1;
        Mat mat = pipeline.getWorkingImage();
        Imgproc.GaussianBlur(mat, mat, new Size(kernelSize, kernelSize), 0);
        return null;
    }
}
