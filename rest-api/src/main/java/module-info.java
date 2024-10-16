module at.fhtw.swkom.paperless.api {
    requires spring.web;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    opens at.fhtw.swkom.paperless.config to spring.core, spring.beans, spring.context;
    opens at.fhtw.swkom.paperless.mapper to spring.core, spring.beans, spring.context;
    opens at.fhtw.swkom.paperless.controller to spring.core, spring.beans, spring.context;
    opens at.fhtw.swkom.paperless.services to spring.core, spring.beans, spring.context;


    requires jakarta.validation;
    requires jakarta.interceptor;
    requires jakarta.cdi;
    requires jakarta.servlet;
    requires io.swagger.v3.oas.annotations;
    requires io.swagger.v3.oas.models;
    requires com.fasterxml.jackson.databind;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires org.openapitools.jackson.nullable;
    requires spring.data.commons;
}