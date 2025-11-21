import os
import zipfile
import shutil

# Define the directory to extract to
extract_dir = 'extracted_files'

# Ensure the extract directory exists
os.makedirs(extract_dir, exist_ok=True)

# Function to organize resources

def organize_resources(file_path):
    resource_types = {
        'images': ['.png', '.jpg', '.jpeg', '.gif'],
        'sounds': ['.mp3', '.wav', '.ogg'],
        'sprites': ['.sprite', '.spr'],
        'data': ['.json', '.xml', '.txt'],
    }
    
    for resource_type, extensions in resource_types.items():
        # Create directory if it doesn't exist
        os.makedirs(os.path.join(extract_dir, resource_type), exist_ok=True)
        
        # Move files to the correct folder
        for file_name in os.listdir(extract_dir):
            if os.path.splitext(file_name)[1] in extensions:
                shutil.move(os.path.join(extract_dir, file_name), os.path.join(extract_dir, resource_type, file_name))

# Main extraction function

def extract_jar(jar_path):
    with zipfile.ZipFile(jar_path, 'r') as jar:
        jar.extractall(extract_dir)
        organize_resources(extract_dir)

# Example usage
# extract_jar('path/to/your/file.jar')
