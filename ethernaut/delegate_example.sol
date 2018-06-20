pragma solidity ^0.4.18;

contract A {
    
    address bAddress;
    
    constructor(address _bAddress) public {
        bAddress = _bAddress;
    }
    
    function callB() public returns (bool) {
        return bAddress.call(bytes4(keccak256("callC()")));
    }
    
    function callcodeB() public returns (bool) {
        return bAddress.callcode(bytes4(keccak256("callcodeC()")));
    }
    
    function delegatecallB() public returns (bool) {
        return bAddress.delegatecall(bytes4(keccak256("delegatecallC()")));
    }
}

contract B {
    
    address cAddress;
    
    constructor(address _cAddress) public {
        cAddress = _cAddress;
    }
    
    function callC() public returns (bool) {
        return cAddress.call(bytes4(keccak256("poke()")));
    }
    
    function callcodeC() public returns (bool) {
        return cAddress.callcode(bytes4(keccak256("poke()")));
    }
    
    function delegatecallC() public returns (bool) {
        return cAddress.delegatecall(bytes4(keccak256("poke()")));
    }
}

contract C {
    
    address lastTX;
    address lastSender;
    
    function poke() public returns (bool) {
        lastTX = tx.origin;
        lastSender = msg.sender;
        return true;
    }
    
    function getLastTx() public view returns (address) {
        return lastTX;
    }
    
    function getLastSender() public view returns (address) {
        return lastSender;
    }
}