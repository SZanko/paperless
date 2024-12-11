#!/usr/bin/env bash

# Script to calculate the Maven repository hash for Nix

REPO_DIR="/tmp/mvn-repo"
rm -rf "$REPO_DIR"
mkdir -p "$REPO_DIR"

echo "Populating local Maven repository..."
mvn dependency:go-offline -Dmaven.repo.local="$REPO_DIR"

echo "Calculating Nix hash..."
nix hash path "$REPO_DIR"
