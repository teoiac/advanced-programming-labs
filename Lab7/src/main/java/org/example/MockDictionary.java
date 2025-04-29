package org.example;


public class MockDictionary extends Dictionary {
    public MockDictionary() {
        super();
    }

    @Override
    public boolean isWord(String str) {
        return true;
    }
}

