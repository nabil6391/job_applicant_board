package com.nabilmh.seekmax.feature.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nabilmh.seekmax.feature.home.model.Job

@Composable
fun JobItemComposable(job: Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 10.dp)
            .clip(RoundedCornerShape(6.dp))
    ) {
        Column(Modifier.padding(10.dp)) {
            Text(
                text = job.positionTitle,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(text = "Company Name ${job.industry}", style = MaterialTheme.typography.caption)
            Spacer(modifier = Modifier.height(15.dp))
            Text(text = job.description, style = MaterialTheme.typography.body2)
            Spacer(modifier = Modifier.height(10.dp))
            if (job.haveIApplied) {
                Row {
                    Text(text = "Applied")
                    Icon(Icons.Filled.CheckCircle, contentDescription = "Applied")
                }
            } else {
                Text(text = "Not Applied")
            }
        }
    }
}
