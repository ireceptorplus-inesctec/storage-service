./network.sh down
docker system prune -a --force
docker volume prune --force
git reset --hard
cd ..
cd ..
sudo chmod 777 ireceptorchain -R
cd ireceptorchain
curl -sSL https://bit.ly/2ysbOFE | bash -s
./gradlew installDist
cd test-network
./network.sh down
./network.sh up createChannel -ca -s couchdb
./network.sh deployCC -ccn ireceptorchain -ccv 1 -cci initLedger -ccl java -ccp ../