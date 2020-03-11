/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgManagedBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * @author Joel
 */
public class MessageSupplier {
    
    private static final String[] positiveMessages = 
        Arrays.asList(
            "Fair enough.",
            "Not bad.",
            "Hmmm... okay.",
            "About time too.",
            "A lucky guess.",
            "I'll accept that.",
            "You know-it-all.",
            "Scavenger!"
        ).toArray(new String[0]);
    
    private static final String[] negativeMessages = 
        Arrays.asList(
            "You plank!",
            "Numbskull!",
            "You fool!",
            "Even I knew that!",
            "What utter stupidity..."
        ).toArray(new String[0]);
    
    public static String getRandomPositiveMessage() {
        
        return positiveMessages[
            new Random().nextInt(positiveMessages.length)
        ];
    }
    
    public static String getRandomNegativeMessage() {
        
        return negativeMessages[
            new Random().nextInt(negativeMessages.length)
        ];
    }
}
