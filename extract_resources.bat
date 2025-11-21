@echo off
setlocal enabledelayedexpansion

set JAR_FILE=s40.kiev.ua_2601_zombieinfection_nokia_5300_ru.jar
set OUTPUT_DIR=extracted_resources
echo Creating output directory...
if not exist "%OUTPUT_DIR%" mkdir "%OUTPUT_DIR%"
echo Extracting JAR file...
jar xf "%JAR_FILE%" -C "%OUTPUT_DIR%"
echo.
echo Creating resource folders...
if not exist "%OUTPUT_DIR%\images" mkdir "%OUTPUT_DIR%\images"
if not exist "%OUTPUT_DIR%\sounds" mkdir "%OUTPUT_DIR%\sounds"
if not exist "%OUTPUT_DIR%\sprites" mkdir "%OUTPUT_DIR%\sprites"
if not exist "%OUTPUT_DIR%\data" mkdir "%OUTPUT_DIR%\data"
echo.
echo Organizing resources by type...
cd "%OUTPUT_DIR%"

REM Move image files
for /r . %%F in (*.png, *.jpg, *.jpeg, *.gif, *.bmp) do (
    if "%%~dpF" neq "%CD%\images\" (
        move "%%F" "images\" 2>nul
    )
)

REM Move sound files
for /r . %%F in (*.wav, *.mp3, *.mid, *.midi) do (
    if "%%~dpF" neq "%CD%\sounds\" (
        move "%%F" "sounds\" 2>nul
    )
)
echo.
echo Extraction complete!
echo Resources extracted to: %OUTPUT_DIR%
echo.
pause
