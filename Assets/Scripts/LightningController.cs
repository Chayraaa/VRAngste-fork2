using UnityEngine;

public class LightningController : MonoBehaviour
{
    public ParticleSystem lightningPS;
    public Skylight skyLight;

    private int lastParticleCount = 0;

    void OnEnable()
    {
        if (lightningPS != null)
        {
            lightningPS.Play();
        }
        lastParticleCount = 0;
    }

    void Update()
    {
        if (lightningPS == null || !lightningPS.IsAlive())
            return;

        int currentCount = lightningPS.particleCount;

        // If new particles spawned this frame, trigger flash
        if (currentCount > lastParticleCount)
        {
            skyLight.TriggerFlash();
            Debug.Log("Sky flash for new particle");
        }

        lastParticleCount = currentCount;
    }
}