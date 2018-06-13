pragma solidity ^0.4.18;

contract Target {
    
    uint256 state = 0;
    
    function setValue(uint256 _value) public {
        state = _value;
    }
    
    function getValue() public view returns (uint256) {
        return state;
    }
}