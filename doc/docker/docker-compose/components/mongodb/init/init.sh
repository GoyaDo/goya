#!/bin/bash

# 等待 MongoDB 启动成功
until mongosh -u "$MONGO_INITDB_ROOT_USERNAME" -p "$MONGO_INITDB_ROOT_PASSWORD" \
  --authenticationDatabase admin --eval "db.runCommand({ ping: 1 })" &>/dev/null; do
  echo "Waiting for MongoDB to start..."
  sleep 1
done

echo " MongoDB is up, proceeding with user initialization..."

mongosh -u "$MONGO_INITDB_ROOT_USERNAME" -p "$MONGO_INITDB_ROOT_PASSWORD" \
  --authenticationDatabase admin --eval "
  db = db.getSiblingDB(\"$MONGO_INITDB_DATABASE\");
  if (!db.getUser(\"$MONGO_OPENIM_USERNAME\")) {
    db.createUser({
      user: \"$MONGO_OPENIM_USERNAME\",
      pwd: \"$MONGO_OPENIM_PASSWORD\",
      roles: [{ role: \"readWrite\", db: \"$MONGO_INITDB_DATABASE\" }]
    });
    print(\" User created: $MONGO_OPENIM_USERNAME\");
  } else {
    print(\"ℹ️  User already exists: $MONGO_OPENIM_USERNAME\");
  }
"