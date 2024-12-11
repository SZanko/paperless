# A WIP docker file which should be replaced by a docker file which uses nix
FROM docker.io/archlinux/archlinux:base-devel


RUN pacman -Syu --noconfirm tesseract tesseract-data-eng tesseract-data-eng jdk21-openjdk

ENV TESSDATA_PREFIX=/usr/share/tessdata/


COPY . .

CMD ["java", "-jar", "target/ocr-worker-1.0-SNAPSHOT-jar-with-dependencies.jar"]
#CMD ["java", "-jar", "target/uberjar/ocr-worker-0.1.0-SNAPSHOT-standalone.jar"]