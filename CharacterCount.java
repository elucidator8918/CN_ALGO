import java.util.*;

public class CharacterCount 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the frame size: ");
        int frameSize = sc.nextInt();
        
        sc.nextLine(); // Consume the newline character
        
        System.out.print("Enter the message: ");
        String message = sc.nextLine();
        
        List<String> frames = frameMessage(message, frameSize);
        System.out.println("Frames:");
        for (String frame : frames)
        System.out.println(frame);        
        
        String reconstructedMessage = reconstructMessage(frames);
        System.out.println("\nReconstructed Message:");
        System.out.println(reconstructedMessage);        
    }
    
    // Frame the message using Character Count Framing
    public static List<String> frameMessage(String message, int frameSize) {
        List<String> frames = new ArrayList<>();
        
        int messageLength = message.length();
        int currentIndex = 0;
        
        while (currentIndex < messageLength) {
            int remainingChars = messageLength - currentIndex;
            int currentFrameSize = Math.min(frameSize, remainingChars);
            
            // Build the frame
            StringBuilder frameBuilder = new StringBuilder();
            frameBuilder.append(currentFrameSize).append(":");
            for (int i = 0; i < currentFrameSize; i++) {
                frameBuilder.append(message.charAt(currentIndex + i));
            }
            
            frames.add(frameBuilder.toString());
            currentIndex += currentFrameSize;
        }
        
        return frames;
    }
    
    // Reconstruct the message from the frames
    public static String reconstructMessage(List<String> frames) {
        StringBuilder messageBuilder = new StringBuilder();
        
        for (String frame : frames) {
            int colonIndex = frame.indexOf(":");
            int frameSize = Integer.parseInt(frame.substring(0, colonIndex));
            
            for (int i = colonIndex + 1; i < colonIndex + 1 + frameSize; i++) {
                messageBuilder.append(frame.charAt(i));
            }
        }
        
        return messageBuilder.toString();
    }
}

