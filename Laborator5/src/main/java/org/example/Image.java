package org.example;
/**
 * Image item is described as a record
 * Each image record has a name, a creationDate, a list of tags and a location in the file system
 */

import java.util.List;

record Image(String name, String creationDate, List<String> tags, String locationPath) {
    public Image(String name, String creationDate, List<String> tags, String locationPath){
        if(name == null || creationDate == null || tags == null || locationPath == null){
            throw new IllegalArgumentException(String.format("Name and creationDate and tags cannot be null"));
        }
        this.name = name;
        this.creationDate = creationDate;
        this.tags = tags;
        this.locationPath = locationPath;
    }
}
