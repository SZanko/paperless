# ocr-worker

OCR the images which it receives from rabbitMQ

## Installation

Set Enviromental Variables like for nixos if you are not on nixos export only TESSDATA_PREFIX and install tesseract
```shell
export LD_LIBRARY_PATH=paperless/.devenv/profile/lib
export TESSDATA_PREFIX=paperless/ocr-worker/resources
```

