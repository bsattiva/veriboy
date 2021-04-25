import React, {useRef, Suspense,  useEffect, useState} from 'react'
import { extend } from 'react-three-fiber'
import { useFrame } from 'react-three-fiber'
import house from '../../textures/models/small-house/house1.glb'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import {useLoader } from 'react-three-fiber';
import * as THREE from "three";
import Building from './Building'

const Houses = (props) => {


var hs = [];
for (let i = 0; i < props.map.pos.length; i++) {
  hs.push( <Building url={props.map.urls[i]} position={props.map.pos[i]} />)
}
 
  

    return (       
         hs    
    )
}

export default Houses
