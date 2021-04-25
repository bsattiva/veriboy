import React, {useRef, useEffect, useState} from 'react'
import * as THREE from "three";
import {useLoader } from 'react-three-fiber';
import { TextureLoader } from 'three/src/loaders/TextureLoader.js';
import grass from '../../textures/grass.jpg'
import { Suspense } from 'react';

const Grid = (props) => {

const grids = [];
const face = [1,0,0];
var [fl, setFl] = useState(props.flags);

const Flag = (props) => {
    const Tile = useLoader(TextureLoader, "/flags.jpg");
 
    const ref = useRef();
    useEffect(() => void ref.current.lookAt(new THREE.Vector3(0,1,0)), [ref.current])
    
    
        return(<mesh receiveShadow rotation={[-Math.PI / 2, 0, 0]} position={props.position} ref={ref}>
            <planeBufferGeometry attach="geometry" args={[1,1]} />
            <meshBasicMaterial map={Tile} attach="material" />
        </mesh>)
 }



const Square = (props) => {
    const Grass = useLoader(TextureLoader, "/grass.jpg");
 
const ref = useRef();
useEffect(() => void ref.current.lookAt(new THREE.Vector3(0,1,0)), [ref.current])


    return(<mesh receiveShadow rotation={[-Math.PI / 2, 0, 0]} position={props.position} ref={ref}>
        <planeBufferGeometry attach="geometry" args={[5,5]} />
        <meshBasicMaterial map={Grass} attach="material" />
    </mesh>)
}

const isFlag = (k,z) => {
    let result = false;
    for (let i = 0; i < fl.length; i++) {
         if (fl[i][0] === k && fl[i][1] === z) {
             result = true;
             break;
         }
    }
    return result;
}


for(var i = 0; i<props.width; i++) {
    for (var j = 0; j<props.length; j++) {  
        // if (i !== 5) {
        // grids.push(<Square position={[i,-0.5,j]}/>)
        // } else {
        //     grids.push(<Flag position={[i,-0.49,j]} />)
        // }

       
            grids.push(<Square position={[i,-0.5,j]}/>)
  

    }
}




    return (
        <>
          {grids}  
        </>
    )
}

export default Grid
