#ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffplay.exe -max_delay 500000 -rtsp_transport udp rtsp://230.0.0.1:4446
#ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffplay.exe rtp://230.0.0.1:4446

# ffmpeg -fflags +genpts -i files\2005-SFSD-sample-mpeg1.mpg  -an -threads 0 -r 10 -g 45 -s 352x240 -deinterlace -f rtp rtp://192.168.200.198:9008 > config.sdp

#ffmpeg/ffmpeg-20170305-035e932-win64-static/bin/ffmpeg.exe ffserver -f ffserver.conf
mvn exec:java -Dexec.mainClass="HostSyncTest"