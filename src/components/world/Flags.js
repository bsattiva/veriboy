import React, {useRef, useEffect} from 'react'
import { TextureLoader } from 'three/src/loaders/TextureLoader.js';
import {useLoader } from 'react-three-fiber';
import * as THREE from "three";
const Flags = (pro) => {

var flags = [];

 const Flag = (props) => {
    const Tile = useLoader(TextureLoader, "/flags.jpg");
 
    const ref = useRef();
    useEffect(() => void ref.current.lookAt(new THREE.Vector3(0,1,0)), [ref.current])
    
    
        return(<mesh receiveShadow rotation={[-Math.PI / 2, 0, 0]} position={props.position} ref={ref}>
            <planeBufferGeometry attach="geometry" args={[1,1]} />
            <meshBasicMaterial map={Tile} attach="material" />
        </mesh>)
 }
 

 for(let i = 0; i<pro.flags.length; i++) {
     flags.push(<Flag position={[pro.flags[i][0], -0.49, pro.flags[i][1]]}/>)
 }

    return (
         flags
    )
}

export default Flags
