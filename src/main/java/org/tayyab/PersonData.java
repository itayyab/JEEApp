/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tayyab;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * @author Tayyab
 */
@XmlRootElement
public class PersonData implements Serializable {
    private long id;
    private String name;
    PersonData() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String namex) {
        this.name = namex;
    }


}  

