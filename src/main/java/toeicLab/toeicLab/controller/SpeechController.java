//package toeicLab.toeicLab.controller;
//
//import com.google.api.gax.core.CredentialsProvider;
//import com.google.api.gax.longrunning.OperationFuture;
//import com.google.cloud.speech.v1.*;
//import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import toeicLab.toeicLab.service.SpeechService2;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class SpeechController {
//
//    private CredentialsProvider credentialsProvider;
//
//    private final SpeechService2 speechService2;
//
//
//    @Autowired
//    public void setCredentialsProvider(CredentialsProvider credentialsProvider){
//        this.credentialsProvider = credentialsProvider;
//    }
//
//    private SpeechSettings settings = null;
//
//    @PostConstruct
//    public void initalize() throws IOException {
//        settings = SpeechSettings.newBuilder().setCredentialsProvider(credentialsProvider).build();
//    }
//
//    @GetMapping("/speech")
//    public Message convertSpeechToText() throws Exception{
//        try(SpeechClient client = SpeechClient.create(settings)){
//            RecognitionConfig.Builder builder = RecognitionConfig.newBuilder().setEncoding(AudioEncoding.FLAC)
//                    .setLanguageCode("en-US").setEnableAutomaticPunctuation(true).setEnableWordTimeOffsets(true);
//            builder.setModel("default");
//
//            RecognitionConfig config = builder.build();
//
//            RecognitionAudio audio = RecognitionAudio.newBuilder().setUri("gs://carbonrider/ffextract.flac").build();
//
//            OperationFuture<LongRunningRecognizeResponse, LongRunningRecognizeMetadata> response = client.longRunningRecognizeAsync(config, audio);
//
//            while(!response.isDone()){
//                Thread.sleep(10000);
//            }
//
//            List<SpeechRecognitionResult> speechResults = response.get().getResultsList();
//
//            StringBuilder transcription = new StringBuilder();
//            for (SpeechRecognitionResult result : speechResults){
//                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
//                transcription.append(alternative.getTranscript());
//            }
//
//            Message message = new Message();
//            message.setContents(transcription.toString());
//
//            return message;
//        }
//    }
//
//
//
//
//}
//
