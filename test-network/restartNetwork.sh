./network.sh down
./network.sh down
./network.sh up createChannel -ca -s couchdb
./network.sh deployCC -ccn ireceptorchain -ccv 1 -cci initLedger -ccl java -ccp ../