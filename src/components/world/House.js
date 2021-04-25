import React, {useRef, Suspense,  useEffect, useState} from 'react'
import { extend } from 'react-three-fiber'
import { useFrame } from 'react-three-fiber'
import house from '../../textures/models/small-house/house1.glb'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import {useLoader } from 'react-three-fiber';
import * as THREE from "three";

const House = (props) => {

    const gltf = useLoader(GLTFLoader, house),
    [geometry, setGeometry] = useState();

    if (!geometry) {    
       const scene = gltf.scene.clone(true); 
    
		setGeometry(scene); 
        
    }  
		 

    const primitiveProps = {
	 
        object: geometry,
		position: props.position,
        scale: [0.2,0.2,0.2]
        
	};

 

   // return <primitive object={gltf.scene} scale={[0.2,0.2,0.2]} position={props.position} />
    
   return <primitive {...primitiveProps} />
    

}

export default House
