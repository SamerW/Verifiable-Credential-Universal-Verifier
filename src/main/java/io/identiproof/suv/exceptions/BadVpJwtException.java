//    Copyright (2021) Crossword Cybersecurity Plc
//
//    This file is part of the Simple Universal Verifier (SUV). 
// 
//    SUV is licensed free of charge. You can redistribute it and/or modify it under the 
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

package io.identiproof.suv.exceptions;

public class BadVpJwtException extends Exception {
    public BadVpJwtException() {
    }

    public BadVpJwtException(String message) {
        super(message);
    }

    public BadVpJwtException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadVpJwtException(Throwable cause) {
        super(cause);
    }

    public BadVpJwtException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
