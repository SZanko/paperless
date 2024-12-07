{
  description = "A Nix flake for a Maven project with a dev shell and package building.";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.11";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils, ... }:
    flake-utils.lib.eachSystem flake-utils.lib.defaultSystems (system: let
      pkgs = import nixpkgs {
        inherit system;
      };

      ocr-worker = pkgs.maven.buildMavenPackage rec {
        pname = "ocr-worker";
        version = "1.0";

        src = self;

        mvnHash = "sha256-jiGTWW2j7ZccGzRJcuOT9kCZWZh3klfYfOmiAQdEirw=";

        nativeBuildInputs = [ pkgs.makeWrapper ];
        #extraSrcs = ["${self}/certs/no-cert-file.crt" ];

        buildOffline = true;

        buildPhase = ''
        #  export MINIO_INSECURE=true
        #  export CERT_FILE=${src}/certs/no-cert-file.crt
        #  export SSL_CERT_FILE=${src}/certs/no-cert-file.crt
          mvn package --offline
        '';

        installPhase = ''
          mkdir -p $out/bin $out/share/ocr-worker
          install -Dm644 ocr-worker/target/ocr-worker-1.0-SNAPSHOT-jar-with-dependencies.jar $out/share/ocr-worker/ocr-worker.jar

          makeWrapper ${pkgs.jre}/bin/java $out/bin/ocr-worker \
            --add-flags "-jar $out/share/ocr-worker/ocr-worker.jar"
        '';

        meta = with pkgs.lib; {
          description = "Simple command line wrapper around JD Core Java Decompiler project";
          homepage = "https://fhtw.at";
          license = licenses.gpl3Plus;
          mainProgramm = "ocr-worker";
        };
      };
    in {
      devShell = pkgs.mkShell {
        name = "maven-devshell";
        buildInputs = with pkgs; [ maven jre makeWrapper tesseract ];
        shellHook = ''
          echo "Dev shell for Maven project ready!"
          echo "Use: mvn clean package"
        '';
      };

      packages = {
        inherit ocr-worker;
      };

      defaultPackage = ocr-worker;
    });
}
