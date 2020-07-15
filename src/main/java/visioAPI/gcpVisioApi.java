package visioAPI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.vision.v1.*;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;

public class gcpVisioApi {

	public static void main(String[] args) {
		try {
			ByteString bytes=ByteString.readFrom(new FileInputStream("src/main/resources/SMS.png"));
			
			Image image= Image.newBuilder().setContent(bytes).build();
			
			Feature feature=Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build();
			
			AnnotateImageRequest annotateImageRequest  =AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
		
			ImageAnnotatorClient client = ImageAnnotatorClient.create();
			
			List<AnnotateImageRequest> requests=new ArrayList();
			
			requests.add(annotateImageRequest);
			
			List<AnnotateImageResponse> responsesList= client.batchAnnotateImages(requests).getResponsesList();
		
			for(AnnotateImageResponse response :responsesList) {
				for(EntityAnnotation en : response.getTextAnnotationsList()) {
					System.out.println(en.getDescription());
				}
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

}
