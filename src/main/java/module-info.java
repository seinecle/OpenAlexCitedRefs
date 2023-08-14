/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */

module net.clementlevallois.bibliocoupling {
    requires jakarta.json;
    requires jakarta.json.bind;
    requires urlbuilder;
    requires java.net.http;
    
    exports net.clementlevallois.bibliocoupling.controller;

}
