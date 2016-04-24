package com.neilmarietta.hipchops.data;

import java.util.ArrayList;
import java.util.List;

public class TestCases {

    public static InputOutput[] sTestArray = new InputOutput[]{
            new InputOutput(
                    "@chris you around?",
                    "{\"mentions\":[\"chris\"]}"),
            new InputOutput(
                    "Good morning! (megusta) (coffee)",
                    "{\"emoticons\":[\"megusta\",\"coffee\"]}"),
            new InputOutput(
                    "Olympics are starting soon; http://www.nbcolympics.com",
                    "{\"links\":[{\"url\":\"http://www.nbcolympics.com\",\"title\":\"NBC Olympics | Home of the 2016 Olympic Games in Rio\"}]}"),
            new InputOutput(
                    "@bob @john (success) such a cool feature; https://twitter.com/jdorfman/status/430511497475670016",
                    "{\"mentions\": [\"bob\",\"john\"],\"emoticons\": [\"success\"],\"links\": [{ \"url\": \"https://twitter.com/jdorfman/status/430511497475670016\", \"title\": \"Justin Dorfman auf Twitter: nice @littlebigdetail from @HipChat (shows hex colors when pasted in chat). http://t.co/7cI6Gjy5pq\" }]}"),
            new InputOutput(
                    "@neil (smile) Check my LinkedIn profile : https://www.linkedin.com/in/neilmarietta",
                    "{\"mentions\":[\"neil\"],\"emoticons\": [\"smile\"],\"links\": [{ \"url\": \"https://www.linkedin.com/in/neilmarietta\", \"title\": \"Neil Marietta | LinkedIn\"}]}")
    };

    private static int sCurrentIndex = 0;

    public static String getNextInput() {
        return sTestArray[sCurrentIndex++ % sTestArray.length].in;
    }

    public static void resetCurrentIndex() {
        sCurrentIndex = 0;
    }

    public static List<String> getInputs() {
        List<String> inputs = new ArrayList<>();
        for (InputOutput inputOutput : TestCases.sTestArray)
            inputs.add(inputOutput.in);
        return inputs;
    }
}
