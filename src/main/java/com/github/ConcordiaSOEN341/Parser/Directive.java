package com.github.ConcordiaSOEN341.Parser;

import com.github.ConcordiaSOEN341.Interfaces.IDirective;

public class Directive implements IDirective {
    private String directive;
    private String cstring;

    public Directive(String d, String cS){
        directive = d;
        cstring = cS;
    }

    @Override
    public String getDirectiveName() {
        return directive;
    }

    @Override
    public String getCString() {
        return cstring;
    }

    public String toString(){
        return directive + " " + cstring;
    }


}
