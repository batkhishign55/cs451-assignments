sudo apt update
sudo apt upgrade -y
sudo apt install git

sudo apt install openjdk-17-jdk
sudo apt install ant

cd ~/
git clone git@github.com:batkhishign55/cs451-hw2.git
cd cs451-hw2
ant

SERVICE_FILE="/etc/systemd/system/hangman.service"

# Create and write systemd service file
sudo bash -c "cat > $SERVICE_FILE << EOF
[Unit]
Description=Huruback server
After=network.target

[Service]
[Unit]
Description=Hangman Game Server
After=network.target

[Service]
User=admin
WorkingDirectory=/home/admin/cs451-hw2/bin
ExecStart=sudo /usr/bin/java -cp /home/admin/cs451-hw2/bin src.BdulamsurankhorServer
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure

[Install]
WantedBy=multi-user.target
EOF"

sudo systemctl daemon-reload
sudo systemctl enable hangman
sudo systemctl start hangman
