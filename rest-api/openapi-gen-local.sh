#!/usr/bin/env bash

openapi-generator-cli generate \
-i ./openapi.yaml \
-g spring \
-p pocoModels=true \
-p useSeperateModelProject=true \
--package-name at.fhtw.swkom.paperless \
--api-package at.fhtw.swkom.paperless.controller \
--model-package at.fhtw.swkom.paperless.services.dto \
--additional-properties configPackage=at.fhtw.swkom.paperless.config \
--additional-properties basePackage=at.fhtw.swkom.paperless.services \
--additional-properties useSpringBoot3=true \
--additional-properties useJakartaEe=true \
-o .
