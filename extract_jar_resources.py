import zipfile
import os

def extract_jar_resources(jar_file_path, output_directory):
    with zipfile.ZipFile(jar_file_path, 'r') as jar:
        # Create the output directory if it doesn't exist
        os.makedirs(output_directory, exist_ok=True)
        
        # Iterate through all the files in the JAR
        for file_info in jar.infolist():
            if file_info.filename.endswith(('.png', '.jpg', '.jpeg', '.gif', '.wav', '.mp3', '.sprite')):
                print(f'Extracting {file_info.filename}...')
                jar.extract(file_info, output_directory)

if __name__ == "__main__":
    # Example usage
    extract_jar_resources('path/to/your.jar', 'output_directory')
