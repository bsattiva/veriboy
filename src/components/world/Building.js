import React, {useMemo} from 'react'
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import {useLoader } from 'react-three-fiber';
import * as THREE from "three";

const Building = (props) => {

  const { scene } = useLoader(GLTFLoader, props.url)
  const copiedScene = useMemo(() => scene.clone(), [scene])

  return (
    <group>
      <primitive castShadow receiveShadow  object={copiedScene} position={props.position} scale={[0.2,0.2,0.2]}/>
    </group>
  );
}

export default Building
