#!/bin/bash
zip -r nutridiary.zip build/libs/nutridiary-0.0.1-SNAPSHOT.jar
aws configure set aws_access_key_id ${API_ACCESS_KEY}
aws configure set aws_secret_access_key ${API_SECRET_KEY}
aws --endpoint-url=https://kr.object.ncloudstorage.com s3 rm s3://nutridiary-object-storage/nutridiary.zip
aws --endpoint-url=https://kr.object.ncloudstorage.com s3 cp nutridiary.zip s3://nutridiary-object-storage/nutridiary.zip
echo 'deploy... to... object storage complete..'
echo 'start... source deploy script..'