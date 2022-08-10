var sql=require('mysql')
module.exports= {
    con: function () {
        var config = {
            port: 3306,
            user: "root",
            password: "",
            host: "localhost",
            database: "Kelaket"
        }
        var connected = sql.createConnection(config)
        console.log("created conection")
        return connected;
    }
}