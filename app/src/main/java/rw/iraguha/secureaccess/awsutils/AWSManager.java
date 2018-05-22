package rw.iraguha.secureaccess.awsutils;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

public class AWSManager {
    static CognitoCachingCredentialsProvider credentialsProvider;
    CognitoSyncManager cognitoSyncManager;
    AmazonS3Client s3Client;
    TransferUtility transferUtility;

    public static CognitoCachingCredentialsProvider getAwsCredentials(Context context){

        if(credentialsProvider == null){
            // Initialize the Amazon Cognito credentials provider
            credentialsProvider= new CognitoCachingCredentialsProvider(
                    context,
                    "us-east-1:ede40529-6aef-454c-a268-4c12fbd417e6", // Identity pool ID
                    Regions.US_EAST_1 // Region
            );
//        cognitoSyncManager = new CognitoSyncManager(context,Regions.US_EAST_1,credentialsProvider);
//        Dataset dataset = cognitoSyncManager.openOrCreateDataset("Mydataset");
//        dataset.put("myKey","myValue");
//        dataset.synchronize(new DefaultSyncCallback());
        }
        return  credentialsProvider;
    }

    public AmazonS3Client initS3Client(Context context){
        if(credentialsProvider == null){
            getAwsCredentials(context);
            s3Client = new AmazonS3Client(credentialsProvider);
            s3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
        }else {
            s3Client = new AmazonS3Client(credentialsProvider);
        }
        return s3Client;
    }

    public TransferUtility checkTransferUtility(AmazonS3Client amazonS3Client,Context context){
        if(transferUtility == null){
            transferUtility = new TransferUtility(amazonS3Client,context);
            return  transferUtility;
        }else{
            return transferUtility;
        }
    }

}
