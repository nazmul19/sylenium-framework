taskkill /f /im chromedriver.exe
taskkill /f /im geckodriver.exe
call mvn clean
call mvn verify -Dselenide.browser=chrome -Dthread.count=10 -Dremote=http://localhost:4444/wd/hub
