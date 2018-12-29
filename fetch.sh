eval $(ssh-agent -s)
ssh-add /c/Users/Niall/.ssh/id_rsa
git config --global remote.origin.url https://github.com/snapscript/snap.git
git remote set-url origin  https://github.com/snapscript/snap.git
git fetch

