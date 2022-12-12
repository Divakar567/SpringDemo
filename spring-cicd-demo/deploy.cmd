@echo OFF
rem This script will deploy job-service as windows service
@setlocal enabledelayedexpansion
:: Setting JOB_SERVICE_PROFILE as environment variable
if [%1] equ [] (
    if [%SERVER_ENV%] equ [] (
        echo Job service profile is not set
        echo Provide profile value as argument or SERVER_ENV system variable
        exit 6
    ) else (
        :: Setting profile value SERVER_ENV value
        setx JOB_SERVICE_PROFILE %SERVER_ENV% /m
    )
) else (
    setx JOB_SERVICE_PROFILE %1 /m
)
if %ERRORLEVEL% neq 0 (
    echo Run Command Prompt as administrator
    exit /B %ERRORLEVEL%
)

echo Initiating maven packaging
call .\mvnw clean package -DskipTests=true
if %ERRORLEVEL% NEQ 0 exit /B %ERRORLEVEL%
echo maven packaging completed

:: Install the service if its not installed as windows service
:: If its already installed and running stop the service
echo Restarting the service
for /f %%i in ('call winsw4.exe status') do set service_status=%%i
if %service_status% equ "NonExistent" (
    call winsw4.exe install
) else (
    call winsw4.exe stop
    timeout /t 5
)
if %ERRORLEVEL% neq 0 exit /b %ERRORLEVEL%

:: Copying jar file from target folder to its parent folder
if exist ".\job-service-0.0.1-SNAPSHOT.jar" del .\job-service-0.0.1-SNAPSHOT.jar
copy .\target\job-service-0.0.1-SNAPSHOT.jar .\
echo Copied resource from .\target\job-service-0.0.1-SNAPSHOT.jar to .\
call winsw4.exe start
@endlocal