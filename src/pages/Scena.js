import React, {useRef, Suspense,  useEffect, useState, useMemo} from 'react'
import {Canvas, useFrame, useThree } from 'react-three-fiber'
import * as THREE from "three";
import { GLTFLoader } from 'three/examples/jsm/loaders/GLTFLoader'
import Grid from '../components/world/Grid'
import House from '../components/world/House'
import './page.css';

const Scena = (props) => {

const style = {
    width: '100%',
    height: '85vh',
    background: 'black'
}

var [position, setPosition] = useState([0,0,1])
var [dir, setDir] = useState(new THREE.Vector3(0,0,0));
var [speed, setSpeed] = useState(0);
var [aspect, setAspect] = useState(0);
var [vaspect, setVaspect] = useState(0);
var [rad, setRad] = useState(0.08);

var [grids, setGrids] = useState([]);

var [map, setMap] = useState([{type: 'house', position: [3,0.2,4]}]);


const Building = (props) => {
    const [house, set] = useState();
    useMemo(() => new GLTFLoader().load("../../textures/models/house.glb", set));

    return house ? <primitive object={house.scene} position={props.position}/> : null;
}


function Light({ brightness, color }) {
    return (
      <hemisphereLight
        width={3}
        height={3}
        intensity={brightness}
        color={color}
        position={[5, 1, 4]}
        lookAt={[0, 0, 0]}
        penumbra={2}
        castShadow
      />
    );
  } 

const keydown = (event) => {
    var key = event.key;

    if(key === 'ArrowUp') {
        setSpeed(0.1);

    }
    if(key === 'ArrowDown') {
        setSpeed(-0.1);
    }
    if(key === 'ArrowLeft') {
        setAspect(aspect + rad);
    }
    if(key === 'ArrowRight') {
        setAspect(aspect - rad);
    }
    if(key === 'q') {
        setVaspect(vaspect + rad)
    }
    if(key === 'w') {
        setVaspect(vaspect - rad)
    } 


}

const keyup = (event) => {

    setSpeed(0);

}



const Camera = (props) => {
    const ref = useRef();
    const {setDefaultCamera} = useThree()

    useEffect(() => void setDefaultCamera(ref.current), [])

    useFrame(() => {
        if (ref.current) {
            ref.current.rotation.y = aspect;
            ref.current.rotation.x = vaspect;
            ref.current.getWorldDirection(dir);
            ref.current.position.addScaledVector(dir, speed);
            setPosition(ref.current.position); 
        }
        

    })

    return <perspectiveCamera ref={ref} {...props} />
}

for (var i = 0; i<map.length; i++) {
    
}

    return (
        <Canvas style={style} tabIndex="0" onKeyDown={keydown} onKeyUp={keyup}>
            <Camera position={position} />
            <Light brightness={1.6} color={"#FFFFFF"}  />
            <Suspense fallback={<>Loading</>}>
                <Grid width={10} length={10} />
            </Suspense>
            <Suspense fallback={<>Loading</>}>
              {/* <House position={[3,0.2,4]} /> */}
                <House position={[3,-0.5,4]} />
            </Suspense>
 
            
            
        </Canvas>
    )
}

export default Scena
 