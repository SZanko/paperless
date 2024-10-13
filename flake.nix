{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-24.05";
    systems.url = "github:nix-systems/default";
    devenv.url = "github:cachix/devenv";
    devenv.inputs.nixpkgs.follows = "nixpkgs";
  };

  nixConfig = {
    extra-trusted-public-keys = "devenv.cachix.org-1:w1cLUi8dv3hnoSPGAuibQv+f9TZLr6cv/Hm9XgU50cw=";
    extra-substituters = "https://devenv.cachix.org";
  };

  outputs = { self, nixpkgs, devenv, systems, ... } @ inputs:
    let
      forEachSystem = nixpkgs.lib.genAttrs (import systems);
    in
    {
      packages = forEachSystem (system: {
        devenv-up = self.devShells.${system}.default.config.procfileScript;
      });

      devShells = forEachSystem
        (system:
          let
            pkgs = nixpkgs.legacyPackages.${system};
          in
          {
            default = devenv.lib.mkShell {
              inherit inputs pkgs;
              modules = [
                {
                  languages.java.enable = true;
                  languages.java.jdk.package = pkgs.zulu17;
                  languages.java.gradle.enable = true;
                  languages.java.gradle.package = pkgs.gradle;
                  difftastic.enable = true;


                  # https://devenv.sh/reference/options/
                  packages = with pkgs; [ 
                    podman-compose
                    pgcli
                    openapi-generator-cli
                  ];

                  enterShell = ''
                    java --version
                  '';

                  processes.hello.exec = "hello";
                }
              ];
            };
          });
    };
}
