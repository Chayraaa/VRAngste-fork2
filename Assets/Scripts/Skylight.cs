using UnityEngine;
using System.Collections;

public class Skylight : MonoBehaviour
{
    public float flashIntensity = 1.5f;
    public float fadeSpeed = 4f;

    float originalIntensity;
    bool flashing = false;

    void Start()
    {
        originalIntensity = RenderSettings.ambientIntensity;
    }

    public void TriggerFlash()
    {
        StartCoroutine(FlashRoutine());
    }

    IEnumerator FlashRoutine()
    {
        flashing = true;
        RenderSettings.ambientIntensity = flashIntensity;

        yield return new WaitForSeconds(0.05f); // short burst

        while (RenderSettings.ambientIntensity > originalIntensity)
        {
            RenderSettings.ambientIntensity = Mathf.Lerp(RenderSettings.ambientIntensity, originalIntensity, Time.deltaTime * fadeSpeed);
            yield return null;
        }

        RenderSettings.ambientIntensity = originalIntensity;
        flashing = false;
    }
}
