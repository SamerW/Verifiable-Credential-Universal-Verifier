//    This file is part of the Simple Universal Verifier (SUV). 
// 
//    SUV is free software: you can redistribute it and/or modify it under the 
//    terms of the GNU Lesser General Public License as published by the Free 
//    Software Foundation, either version 3 of the License, or (at your 
//    option) any later version. 
// 
//    SUV is distributed in the hope that it will be useful,but WITHOUT ANY 
//    WARRANTY; without even the implied warranty of MERCHANTABILITY or 
//    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public 
//    License for more details. 
// 
//    You should have received a copy of the GNU Lesser General Public License 
//    along with SUV.  If not, see <https://www.gnu.org/licenses/>. 

package io.identiproof.suv.model;

import java.util.List;

public class Verifier {
    private String url;
    private String description;
    private String didMethod;
    private List<String> signatureAlgorithm;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDidMethod() {
        return didMethod;
    }

    public void setDidMethod(String didMethod) {
        this.didMethod = didMethod;
    }

    public List<String> getSignatureAlgorithm() {
        return signatureAlgorithm;
    }

    public void setSignatureAlgorithm(List<String> signatureAlgorithm) {
        this.signatureAlgorithm = signatureAlgorithm;
    }
}
