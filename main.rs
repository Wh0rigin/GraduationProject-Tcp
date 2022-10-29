use std::io::prelude::*;
use std::net::{TcpListener,TcpStream};

use std::time;
use std::thread;

fn main(){
    let handle_server = thread::spawn(move||{
        println!("[Log]:start...");
        worker_server();
        println!("[Log]:close...");
    });
    handle_server.join();
}

fn worker_server(){
    let mut thread_Vec: Vec<thread::JoinHandle<()>> = Vec::new();
    let listener = TcpListener::bind("127.0.0.1:8600");
    if !listener.is_ok(){
        println!("[Log]:bind error...");
        return;
    }
    let listener = listener.unwrap();
    for stream in listener.incoming(){
        let stream = stream.expect("failed");
        // if !stream.is_ok(){
        //     println!("[Log]:error...");
        //     continue;
        // }
        let handle = thread::spawn(move || {
            process_stream(stream);
        });
        thread_Vec.push(handle);
        // if false == process_stream(stream){
        //     println!("[Log]:error...");
        //     continue;
        // }
    }
}

fn process_stream(mut stream:TcpStream)->bool{
    let mut buffer = [0;1024];
    if !stream.read(&mut buffer).is_ok()
    {
        return false;
    }
    println!("{}",String::from_utf8_lossy(&buffer[..]));
    if !stream.write(b"Server has received").is_ok(){
        return false;
    }
    return true;
}