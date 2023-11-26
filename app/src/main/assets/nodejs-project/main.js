var http = require('http');
var https = require('https');
var teeworlds = require('teeworlds');
var socketIo = require('socket.io');

var javaServer = http.createServer();
let io = socketIo(javaServer);




let client;



	
io.on('connection', (javaServer) => {

	javaServer.on('communication', (data) => {
		
		//get masters
		if (typeof data.getMasters != "undefined") {
			fetchMasters('https://master1.ddnet.org/ddnet/15/servers.json', (err, masters) => {
				io.emit('getMasters', masters);
			});
		}
		
		
		//send message
		if (typeof data.sendMessage != "undefined") {
			 client.game.Say(data.sendMessage, false);
		}
		

		// connect to the selected server
		//{"address":"[tw-0.6+udp:\/\/5.78.73.17:8306, tw-0.7+udp:\/\/5.78.73.17:8306]"}
		if (data.hasOwnProperty('address')) {
			let addresses = data.address;

			// Odstranění hranatých závorek a rozdělení do pole
			addresses = addresses.replace('[', '').replace(']', '');
			const addressArray = addresses.split(', ');

			// Hledání adresy pro verzi 0.6
			const address06 = addressArray.find(addr => addr.includes('tw-0.6'));
			if (address06) {
				// Extrahování IP adresy a portu
				const parts = address06.split('//')[1].split(':');
				const ip = parts[0];
				const port = parts[1];

			} 
		
	//client = new teeworlds.Client(ip, port, "Axaris");

			client = new teeworlds.Client("51.91.30.52", 8404, "Ax");
			client.connect();
			
			client.on("message", message => {
				/* {
					team: 0,
					client_id: 14,
					message: 'a',
					author: {
						ClientInfo: {
							name: 'Nudelsaft c:',
							clan: '',
							country: 276,
							skin: 'coala_toptri',
							use_custom_color: 0,
							color_body: 4718592,
							color_feet: 5046016
						},
						PlayerInfo: { local: 0, client_id: 4, team: 0, score: 36, latency: 0 }
					}
				}
				*/
			   // var msg = message.author.ClientInfo.name + ": " + message.message;
				io.emit("chatMessage", message);
			});
			
			client.on("snapshot", () => {
				var snapshot = [];

				for (var id = 0;id<128;id++){
					   snapshot.push({ 
							"ClientInfo" : client.SnapshotUnpacker.getObjClientInfo(id),
							"PlayerInfo"  :  client.SnapshotUnpacker.getObjPlayerInfo(id),
							"Character"       : client.SnapshotUnpacker.getObjCharacter(id)
						});

				
				
				}
				
				io.emit("snapshot", snapshot);
				
			});
		}
	});
	
	


	
});
javaServer.listen(3000); //socket.io srv



function fetchMasters(url, callback) {
    https.get(url, (res) => {
        let data = '';

        res.on('data', (chunk) => {
            data += chunk;
        });

        res.on('end', () => {
            try {
                const jsonData = JSON.parse(data);
                callback(null, jsonData);
            } catch (error) {
                callback(error, null);
            }
        });
    }).on('error', (error) => {
        callback(error, null);
    });
}
