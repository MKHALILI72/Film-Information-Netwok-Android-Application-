module.exports={
    getuserfavoritelist:function (req,resp) {
        var connectdb=require("../basefunction/connectDb")
        var checkUserLogin=require("../basefunction/checkUserLogin")
        var con=connectdb.con()
        con.connect()
        console.log("connected!!!")

        checkUserLogin.checkUserLogin(req.body['email'],req.body['password'],function (returnvalue) {
            if(returnvalue)
            {
                var query=`SELECT *  FROM film inner join addfavorite on addfavorite.f_id=film.f_id where u_id=${req.body['u_id']}`
                console.log(query)
                con.query(query,function (err,result,field) {
                    if(result.length>0){

                            var myresult=[]
                            for(var i=0;i<result.length;i++) {
                                myresult[i] = {
                                    status: true,
                                    id: result[i].f_id,
                                    name: result[i].f_name,
                                    poster: result[i].poster,
                                    director: result[i].f_director,
                                    createdate: result[i].f_createdate,
                                    rate: result[i].f_rate,
                                    nvote: result[i].f_nvote
                                }
                            }
                        console.log(myresult)
                        resp.setHeader('Content-Type', 'application/json');
                        resp.send(myresult)
                    }
                    else{

                        resp.setHeader('Content-Type', 'application/json');
                        resp.send({status:false})
                    }
                })
            }
            else{

                resp.setHeader('Content-Type', 'application/json');
                resp.send({status:false})
            }

        })

    }
}