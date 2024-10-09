./bin/kafka-topics.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --replication-factor 3 --create --topic Topic_Four --partitions 4 --config retention.ms=36000000
./bin/kafka-topics.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --replication-factor 2 --create --topic Topic_Three --partitions 3 --config retention.ms=36000000
./bin/kafka-topics.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --replication-factor 2 --create --topic Topic_One --partitions 1 --config retention.ms=36000000

./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --list

./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_One --command-config ./config/admin.conf --consumer --group='*'
./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_Three --command-config ./config/admin.conf --consumer --group='*'
./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_Four --command-config ./config/admin.conf --consumer --group='*'
./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_One --command-config ./config/admin.conf --producer --group='*'
./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_Three --command-config ./config/admin.conf --producer --group='*'
./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --add --allow-principal User:usercc --topic Topic_Four --command-config ./config/admin.conf --producer --group='*'

./bin/kafka-acls.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --list

./bin/kafka-topics.sh --bootstrap-server bdulamsurankhor-1.novalocal:9092 --command-config ./config/admin.conf --describe
