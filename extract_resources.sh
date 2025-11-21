#!/bin/bash

# Script to extract JAR resources on Linux and Mac systems

if [ $# -eq 0 ]; then
    echo "Usage: $0 <jarfile>"
    exit 1
fi

JARFILE=$1

if [ ! -f "$JARFILE" ]; then
    echo "File not found: $JARFILE"
    exit 1
fi

# Create a directory to extract the contents
DIR_NAME="extracted_$(basename "$JARFILE" .jar)"
mkdir -p "$DIR_NAME"

# Extract the contents of the JAR file
unzip -q "$JARFILE" -d "$DIR_NAME"

echo "Resources extracted to: $DIR_NAME"