# expo-in-app-update

Android in-app update module for Expo (Immediate update).

## Installation

npx expo install expo-in-app-update

## Usage

import { triggerImmediateUpdate } from 'expo-in-app-update';

useEffect(() => {
  triggerImmediateUpdate();
}, []);

## Platform
- Android only